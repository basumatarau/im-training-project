package com.basumatarau.imProject.convertor.config;

import com.basumatarau.imProject.convertor.cutomConvertors.*;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.*;

@Configuration
@ComponentScan(basePackages =
        {"com.basumatarau.imProject.convertor.cutomConvertors"})
public class ModelMapperConfig {

    @Bean
    @Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
    public ModelMapper modelMapper() {
        final ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(getUserToUserProfileDtoConverter());
        modelMapper.addConverter(new UserToUserProfileDto());
        modelMapper.addConverter(new ChatRoomToChatRoomDto());
        modelMapper.addConverter(getContactToPersonalContactVo());
        modelMapper.addConverter(getSubscriptionToSubscriptionVo());
        modelMapper.addConverter(getPrivateMessageToMessageDtoConverter());
        modelMapper.addConverter(messageStatusInfoToMessageStatusInfoDto());
        modelMapper.addConverter(getPrivateMessageToMessageWithDetailsDtoConverter());
        modelMapper.addConverter(new SearchCriteriaDtoToUserSearchSpec());
        modelMapper.addConverter(new MessageResourceDtoToMessageResource());
        return modelMapper;
    }

    @Bean
    public ContactToPersonalContactVo getContactToPersonalContactVo(){
        return new ContactToPersonalContactVo();
    }

    @Bean
    public SubscriptionToSubscriptionVo getSubscriptionToSubscriptionVo(){
        return new SubscriptionToSubscriptionVo();
    }

    @Bean
    public UserToUserProfileDto getUserToUserProfileDtoConverter(){
        return new UserToUserProfileDto();
    }

    @Bean
    public PrivateMessageToMessageDto getPrivateMessageToMessageDtoConverter(){
        return new PrivateMessageToMessageDto();
    }

    @Bean
    public MessageStatusInfoToMessageStatusInfoDto messageStatusInfoToMessageStatusInfoDto(){
        return new MessageStatusInfoToMessageStatusInfoDto();
    }

    @Bean
    public PrivateMessageToMessageWithDetailsDto getPrivateMessageToMessageWithDetailsDtoConverter(){
        return new PrivateMessageToMessageWithDetailsDto();
    }
}
