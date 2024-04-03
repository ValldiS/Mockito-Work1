package ru.netology.patient.service.medical;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import ru.netology.patient.entity.BloodPressure;
import ru.netology.patient.entity.HealthInfo;
import ru.netology.patient.entity.PatientInfo;
import ru.netology.patient.repository.PatientInfoFileRepository;
import ru.netology.patient.repository.PatientInfoRepository;
import ru.netology.patient.service.alert.SendAlertService;
import ru.netology.patient.service.alert.SendAlertServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class MedicalServiceImplTest {


    @Mock
    PatientInfoRepository patientInfoRepository = Mockito.mock(PatientInfoFileRepository.class);
    @Mock
    SendAlertService alertService = Mockito.mock(SendAlertServiceImpl.class);

    //Проверка вызова сообщений при проверке давления.
    @Test
    void BloodPressure_massage() {
        String id1 = "";
        Mockito.when(patientInfoRepository.getById(id1))
                .thenReturn((new PatientInfo("Иван", "Петров", LocalDate.of(1980, 11, 26),
                        new HealthInfo(new BigDecimal("36.65"), new BloodPressure(120, 80)))));


        MedicalService medicalService = new MedicalServiceImpl(patientInfoRepository, alertService);

        BloodPressure currentPressure = new BloodPressure(60, 120);
        medicalService.checkBloodPressure(id1, currentPressure);


        Mockito.verify(alertService, Mockito.times(1)).send(Mockito.anyString());
    }

    //Проверка вызова сообщений при проверке темпиратуры.
    @Test
    void BigDecimal_message() {
        String id1 = "";

        Mockito.when(patientInfoRepository.getById(id1))
                .thenReturn((new PatientInfo("Иван", "Петров", LocalDate.of(1980, 11, 26),
                        new HealthInfo(new BigDecimal("38.65"), new BloodPressure(120, 80)))));


        MedicalService medicalService = new MedicalServiceImpl(patientInfoRepository, alertService);

        BigDecimal currentTemperature = new BigDecimal("36.6");
        medicalService.checkTemperature(id1, currentTemperature);

        Mockito.verify(alertService, Mockito.times(1)).send(Mockito.anyString());
    }

    //Проверяем, что сообщения не выводятся при нормальных показателях.
    @Test
    void normal_indicators() {
        String id1 = "";

        Mockito.when(patientInfoRepository.getById(id1))
                .thenReturn((new PatientInfo("Иван", "Петров", LocalDate.of(1980, 11, 26),
                        new HealthInfo(new BigDecimal("36.65"), new BloodPressure(120, 80)))));


        MedicalService medicalService = new MedicalServiceImpl(patientInfoRepository, alertService);

        BloodPressure currentPressure = new BloodPressure(120, 80);
        medicalService.checkBloodPressure(id1, currentPressure);

        BigDecimal currentTemperature = new BigDecimal("36.6");
        medicalService.checkTemperature(id1, currentTemperature);

        Mockito.verify(alertService, Mockito.times(0)).send(Mockito.anyString());
    }




}