package com.github.javalab.javaiaas.services;

import com.github.javalab.javaiaas.models.Instance;
import com.github.javalab.javaiaas.repositories.InstanceRepository;
import com.github.javalab.javaiaas.repositories.UsersRepository;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class InstanceServiceImpl implements InstanceService {
    @Autowired
    private InstanceRepository repository;
    @Autowired
    private PortService portService;
    private String directory;
    private ProcessBuilder processBuilder = new ProcessBuilder();
    @Autowired
    private UsersRepository usersRepository;


    @Override
    public Instance createCopy(Instance instance) throws IOException, InterruptedException {
        Integer freePort = portService.allocatePorts(1).get(0); // get free port
        Long newId = repository.findMaxId() + 1;
        directory = instance.getRepoName();
        //need to run docker dind with github project name: ( docker run --privileged -d --name=${repoName} docker:dind)
        //instance copy
        processBuilder.command("bash", "docker exec  -i " + directory + " cp -R /usr/bin/" + directory + "/" + instance.getInstanceName()
                + "/usr/bin/" + directory + "/" + instance.getInstanceName());
        processBuilder.inheritIO();
        Process process = processBuilder.start();
        //application.yml
        processBuilder.command("bash", "docker exec  -i " + directory + " echo -e \"    " + instance.getInstanceName() + newId + ": \\n     path:" + newId + "/" + instance.getInstanceUrl() + "/**" + " \n     " + instance.getInstanceName() + newId + " \n >> $(find -name application.yml)");
        Process pc2 = processBuilder.start();
        //application.properties of instance
        processBuilder.command("bash", "docker exec -i " + directory + " sed -i 's/" + instance.getInstanceName() + " */" + instance.getInstanceName() + newId + "/' /usr/bin/" + instance.getRepoName() + "/" + instance.getInstanceName() + "/src/main/resources/application.properties");
        Process p3 = processBuilder.start();
        //build copy
        processBuilder.command("bash", "docker exec -i " + directory + " docker build --tag " + instance.getInstanceName() + newId + ":latest /usr/bin/" + directory + "/" + instance.getInstanceName());
        Process p4 = processBuilder.start();
        //run copy
        processBuilder.command("bash", "docker exec -i " + directory + " docker run -p " + freePort + "${grep -oE '\\b[0-9]{1,3}{3}\\b'} /usr/bin/" + directory + "/" + instance.getInstanceName() + "/Dockerfile");
        Process p5 = processBuilder.start();
        process.waitFor();
        pc2.waitFor();
        p3.waitFor();
        p4.waitFor();
        p5.waitFor();
        Instance copyInst = new Instance(newId, newId + "/" + instance.getInstanceUrl(), instance.getInstanceName(),
                freePort, instance.getRepoName(), "active", instance.getApplication(), instance.getUser());
        repository.save(copyInst);
        return copyInst;
    }

    @Override
    public void stopInstance(Long id) {
        Optional<Instance> inst = repository.findById(id);
        processBuilder.command("bash", "docker exec -i " + inst.get().getRepoName() + " docker stop " + inst.get().getInstanceName());
        String status = "not active";
        repository.stopInstance(status, id);
    }

    @Override
    public void startInstance(Long id) {
        Optional<Instance> inst = repository.findById(id);
        processBuilder.command("bash", "docker exec -i " + inst.get().getRepoName() + " docker start " + inst.get().getInstanceName());
        String status = "active";
        repository.startInstance(status, id);
    }

    @Override
    public List<Instance> getAll(String username) {
        return repository.findAllByUserId(usersRepository.findByLogin(username).get().getId());
    }
}
