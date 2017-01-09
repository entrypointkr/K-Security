package cloud.swiftnode.kspam.abstraction.convertor;

import cloud.swiftnode.kspam.abstraction.Info;
import cloud.swiftnode.kspam.abstraction.ObjectConverter;

/**
 * Created by Junhyeong Lim on 2017-01-10.
 */
public class ObjectToInfoConverter extends ObjectConverter<Info> {
    public ObjectToInfoConverter(Object obj) {
        super(obj);
    }

    @Override
    public Info convert() {
        return null;
    }
}
