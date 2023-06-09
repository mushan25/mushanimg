package com.hzb.file.imageclass.gatewayimpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hzb.file.convertor.ImageclassConvertor;
import com.hzb.file.convertor.ImageclassConvertorImpl;
import com.hzb.file.domain.imageclass.gateway.ImageclassGateway;
import com.hzb.file.domain.imageclass.model.entities.Imageclass;
import com.hzb.file.imageclass.gatewayimpl.database.ImageclassMapper;
import com.hzb.file.imageclass.gatewayimpl.database.dataobject.ImageclassDO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * @author: hzb
 * @Date: 2023/6/9
 */
@ExtendWith(MockitoExtension.class)
class ImageclassGatewayImplTest {
    @Mock
    private ImageclassMapper imageclassMapper;
    private ImageclassGateway underTest;
    @Mock
    private ImageclassConvertor imageclassConvertor;

    @BeforeEach
    void setUp() {
        underTest = new ImageclassGatewayImpl(imageclassMapper);
    }

    @Test
    void canGetImageclassList() {

        // given
        Long userId = 1L;

        // when
        underTest.getImageclassList(userId);

        // then
        Mockito.verify(imageclassMapper).selectList(Mockito.any());
        Mockito.verify(imageclassConvertor).DOs2Imageclasses(Mockito.anyList());
    }

    @Test
    void canGetImageclssInfo() {

        // given
        Imageclass imageclass = new Imageclass();
        imageclass.setUserId(1L);
        imageclass.setId(1L);
        imageclass.setImgclassName("test");

        LambdaQueryWrapper<ImageclassDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ImageclassDO::getUserId, imageclass.getUserId())
                .eq(ImageclassDO::getId, imageclass.getId());



        // when
        underTest.getImageclssInfo(imageclass);

        // then
        ArgumentCaptor<LambdaQueryWrapper<ImageclassDO>> captor = ArgumentCaptor.forClass(LambdaQueryWrapper.class);
        Mockito.verify(imageclassMapper).selectOne(captor.capture());

        LambdaQueryWrapper<ImageclassDO> captorValue = captor.getValue();

        assertThat(captorValue).isEqualTo(wrapper);
    }
}