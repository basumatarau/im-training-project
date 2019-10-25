package com.basumatarau.imProject.convertor.cutomConvertors;

import com.basumatarau.imProject.persistence.model.ImageResource;
import com.basumatarau.imProject.persistence.model.MessageResource;
import com.basumatarau.imProject.serializer.customDto.MessageResourceDto;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UncheckedIOException;

public class MessageResourceDtoToMessageResource
        implements Converter<MessageResourceDto, MessageResource> {

    @Override
    public MessageResource convert(
                    MappingContext<MessageResourceDto,
                    MessageResource> mappingContext) {

        if(mappingContext.getSource() == null){
            return null;
        }

        try {
            final MessageResourceDto.MessageResourceType messageResourceType =
                    mappingContext
                        .getSource()
                        .getType();

            switch (messageResourceType){
                case FILE:
                    return new MessageResource.MessageResourceBuilder()
                            .data(mappingContext.getSource().getBinData())
                            .name(mappingContext.getSource().getResourceName())
                            .message(null)
                            .build();
                case IMAGE:
                    final BufferedImage bufferedImage = ImageIO.read(
                            new ByteArrayInputStream(
                                    mappingContext.getSource().getBinData()
                            )
                    );
                    return new ImageResource.ImageResourceBuilder()
                            .data(mappingContext.getSource().getBinData())
                            .name(mappingContext.getSource().getResourceName())
                            .height(bufferedImage.getHeight())
                            .width(bufferedImage.getWidth())
                            .message(null)
                            .build();
                default:
                    throw new UnsupportedOperationException(
                            "unsupported message resource type: " + messageResourceType
                    );
            }
        } catch (InstantiationException e) {
            throw new UnsupportedOperationException(e);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
