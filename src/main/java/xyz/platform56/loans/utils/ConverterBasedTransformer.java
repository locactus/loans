package xyz.platform56.loans.utils;

import com.google.common.base.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class ConverterBasedTransformer<S, D> implements Function<S, D> {


    @Autowired
    @Qualifier("loansConversionService")
    private ConversionService conversionService;

    private Class<D> targetType;

    public ConverterBasedTransformer(Class<D> targetType) {
        this.targetType = targetType;
    }

    public ConverterBasedTransformer() {

    }

    @Override
    public D apply(S s) {
        return conversionService.convert(s, targetType);
    }

}
