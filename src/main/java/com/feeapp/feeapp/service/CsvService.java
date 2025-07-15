package com.feeapp.feeapp.service;

import com.feeapp.feeapp.entity.Fee;
import com.feeapp.feeapp.entity.Student;
import com.feeapp.feeapp.repository.FeeRepository;
import com.feeapp.feeapp.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class CsvService {

    private final StudentRepository studentRepository;
    private final FeeRepository feeRepository;

    public void upload(MultipartFile file) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
             CSVParser parser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader)) {
            for (CSVRecord record : parser) {
                String name = record.get("name");
                String phone = record.get("phone");
                BigDecimal amount = new BigDecimal(record.get("amount"));
                LocalDate dueDate = LocalDate.parse(record.get("dueDate"));

                Student student = studentRepository.save(Student.builder().name(name).phone(phone).build());
                Fee fee = Fee.builder()
                        .student(student)
                        .amount(amount)
                        .dueDate(dueDate)
                        .paid(false)
                        .build();
                feeRepository.save(fee);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse CSV", e);
        }
    }
}
