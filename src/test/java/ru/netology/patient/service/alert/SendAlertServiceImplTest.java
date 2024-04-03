package ru.netology.patient.service.alert;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class SendAlertServiceImplTest {

    @Mock
    SendAlertService alertService = Mockito.mock(SendAlertServiceImpl.class);

    //Проверяем работу метода send.
    @Test
    void correct_send_message() {
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        alertService.send("test");
        Mockito.verify(alertService).send(argumentCaptor.capture());
        Assertions.assertEquals("test",argumentCaptor.getValue());
    }
}