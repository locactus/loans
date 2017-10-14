package xyz.platform56.loans.utils;

import com.google.common.base.Function;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ModelMapperBasedTransformer<S, D> implements Function<S, D> {


    @Autowired
    private ModelMapper modelMapper;

    private Class<D> targetType;

    public ModelMapperBasedTransformer(Class<D> targetType) {
        this.targetType = targetType;
    }

    public ModelMapperBasedTransformer() {

    }

    @Override
    public D apply(S s) {

        return modelMapper.map(s, targetType);
    }

}
