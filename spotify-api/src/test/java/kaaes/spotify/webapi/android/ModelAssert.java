package kaaes.spotify.webapi.android;

import android.os.Parcelable;

import org.fest.assertions.api.AbstractAssert;
import org.fest.assertions.api.Assertions;

import java.lang.reflect.Field;
import java.util.List;

public class ModelAssert extends AbstractAssert<ModelAssert, Parcelable> {

    public static final String ERROR_MESSAGE = "\nExpected : <%s> \nActual   : <%s> \nat %s";

    protected ModelAssert(Parcelable actual, Class<?> selfType) {
        super(actual, selfType);
    }

    public static ModelAssert assertThat(Parcelable actual) {
        return new ModelAssert(actual, ModelAssert.class);
    }

    public ModelAssert isEqualByComparingFields(Parcelable expected) {

        Field[] fields = expected.getClass().getFields();
        for (Field field : fields) {
            try {
                String fieldName = expected.getClass().getSimpleName() + "#" + field.getName();

                Object actualField = field.get(actual);
                Object expectedField = field.get(expected);

                if (isList(field)) {
                    compareLists(fieldName, (List) actualField, (List) expectedField);
                } else {

                    // The maps in current models only contain simple types so we skip any fancy
                    // comparisons and use regular compare

                    compareFields(fieldName, expectedField, actualField);
                }

            } catch (IllegalAccessException e) {
                throw new AssertionError("Can't access fields");
            }
        }

        return this;
    }

    private void compareLists(String fieldName, List actual, List expected) {
        if (actual == null || expected == null) {
            compareFields(fieldName, expected, actual);
            return;
        }

        for (int i = 0; i < actual.size(); i++) {
            compareFields(fieldName, expected.get(i), actual.get(i));
        }
    }

    private boolean isList(Field field) {
        return List.class.isAssignableFrom(field.getType());
    }

    private AbstractAssert compareFields(String fieldName, Object expected, Object actual) {
        if (actual instanceof Parcelable) {
            return ModelAssert.assertThat((Parcelable) actual)
                    .isEqualByComparingFields((Parcelable) expected);
        }

        // Be nice and show which field in which class is failing
        String fieldPath = this.actual.getClass().getSimpleName() + "#" + fieldName;

        return Assertions.assertThat(actual)
                .overridingErrorMessage(ERROR_MESSAGE, expected, actual, fieldPath)
                .isEqualTo(expected);
    }
}
