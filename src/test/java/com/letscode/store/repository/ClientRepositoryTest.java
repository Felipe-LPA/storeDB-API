package com.letscode.store.repository;

import com.letscode.store.model.Client;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@DataJpaTest
@ExtendWith(SpringExtension.class)
public class ClientRepositoryTest {

    @Autowired
    private ClientRepository clientRepository;

    @Test
    public void testNotFoundCpf(){
        Optional<Client> client = clientRepository.findClientByCpf("123");
        Assertions.assertFalse(client.isPresent());
    }

    @Test
    public void testFoundCpf(){
        Optional<Client> client = clientRepository.findClientByCpf("89247332060");
        Assertions.assertTrue(client.isPresent());
    }
}
