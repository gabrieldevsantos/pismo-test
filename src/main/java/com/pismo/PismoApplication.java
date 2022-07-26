package com.pismo;

import com.pismo.data.entities.OperationTypeEntity;
import com.pismo.data.repository.OperationTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableJpaRepositories
@EntityScan
@RequiredArgsConstructor
public class PismoApplication {

    private final OperationTypeRepository operationTypeRepository;

	public static void main(String[] args) {
        SpringApplication.run(PismoApplication.class, args);
	}

	@PostConstruct
    public void execute() {
	    operationTypeRepository.save(new OperationTypeEntity(1, "COMPRA A VISTA"));
	    operationTypeRepository.save(new OperationTypeEntity(2, "COMPRA PARCELADA"));
	    operationTypeRepository.save(new OperationTypeEntity(3, "SAQUE"));
	    operationTypeRepository.save(new OperationTypeEntity(4, "PAGAMENTO"));
    }

}
