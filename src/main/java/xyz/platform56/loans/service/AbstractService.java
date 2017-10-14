package xyz.platform56.loans.service;


import xyz.platform56.loans.config.SearchResponseConverter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;

@Setter
public abstract class AbstractService {


    @Autowired
    protected SearchResponseConverter searchResponseConverter;



}
