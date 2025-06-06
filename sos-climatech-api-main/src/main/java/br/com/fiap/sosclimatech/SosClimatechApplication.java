package br.com.fiap.sosclimatech;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
public class SosClimatechApplication {
    public static void main(String[] args) {
        SpringApplication.run(SosClimatechApplication.class, args);
        System.out.println("✅ SOS ClimaTech rodando!");
    }
}