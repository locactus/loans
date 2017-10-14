package xyz.platform56.loans.config;


import org.modelmapper.ModelMapper;
import xyz.platform56.loans.entity.CustomerEntity;
import xyz.platform56.loans.entity.IdentificationEntity;
import xyz.platform56.loans.pojo.*;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.platform56.loans.utils.ModelMapperBasedTransformer;


@Configuration
@EnableAutoConfiguration
public class ConversionConfig {



    @Bean(name = "customerEntityCustomerModelMapperBasedTransformer")
    public ModelMapperBasedTransformer<CustomerEntity, LoanDetails> customerEntityCustomerModelMapperBasedTransformer() {
        return new ModelMapperBasedTransformer<>(LoanDetails.class);

    }

    @Bean(name = "identificationEntityIdentificationPojoModelMapperBasedTransformer")
    public ModelMapperBasedTransformer<IdentificationEntity, Identification>
    identificationEntityIdentificationPojoModelMapperBasedTransformer() {
        return new ModelMapperBasedTransformer<>(Identification.class);

    }


    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }


}