package br.com.fiap.grupo30.fastfood.payments_api.infrastructure.converters;

import java.time.Instant;
import software.amazon.awssdk.enhanced.dynamodb.AttributeConverter;
import software.amazon.awssdk.enhanced.dynamodb.AttributeValueType;
import software.amazon.awssdk.enhanced.dynamodb.EnhancedType;
import software.amazon.awssdk.enhanced.dynamodb.internal.converter.attribute.InstantAsStringAttributeConverter;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

public class InstantAsStringAttributeConverterWithConstructor
        implements AttributeConverter<Instant> {
    private static final InstantAsStringAttributeConverter CONVERTER =
            InstantAsStringAttributeConverter.create();

    @Override
    public AttributeValue transformFrom(Instant instant) {
        return CONVERTER.transformFrom(instant);
    }

    @Override
    public Instant transformTo(AttributeValue attributeValue) {
        return CONVERTER.transformTo(attributeValue);
    }

    @Override
    public EnhancedType<Instant> type() {
        return CONVERTER.type();
    }

    @Override
    public AttributeValueType attributeValueType() {
        return CONVERTER.attributeValueType();
    }
}
