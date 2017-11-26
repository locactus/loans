package xyz.platform56.loans.config;


import com.google.common.collect.Sets;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import xyz.platform56.loans.entity.LoanEntity;
import xyz.platform56.loans.pojo.*;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.platform56.loans.utils.ModelMapperBasedTransformer;

import java.util.Set;


@Configuration
@EnableAutoConfiguration
public class ConversionConfig {

    @Bean
    @Qualifier("loansConversionService")
    public ConversionService loansConversionService() {
        ConversionServiceFactoryBean factory = new ConversionServiceFactoryBean();
        Set<Converter> converters = Sets.newHashSet();
        factory.afterPropertiesSet();
        return factory.getObject();
    }


    @Bean(name = "loanEntityToLoanDetailsModelMapperBasedTransformer")
    public ModelMapperBasedTransformer<LoanEntity, LoanDetailsResponse> loanEntityToLoanDetailsModelMapperBasedTransformer() {
        return new ModelMapperBasedTransformer<>(LoanDetailsResponse.class);

    }



    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }


}