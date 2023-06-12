package com.hzb.file.domain.ability;

import com.hzb.file.domain.image.gateway.ImageGateway;
import com.hzb.file.domain.image.model.entities.Image;
import com.hzb.file.domain.imageclass.gateway.ImageclassGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author: hzb
 * @Date: 2023/6/12
 */
@ExtendWith(MockitoExtension.class)
class DomainServiceTest {
    private DomainService underTest;
    @Mock
    private ImageGateway imageGateway;
    @Mock
    private ImageclassGateway imageclassGateway;

    @BeforeEach
    void setUp() {
        underTest = new DomainService(imageGateway, imageclassGateway, underTest);
    }

    @Test
    void canDeleteImg() {
        // given
        List<Long> imgIds = new ArrayList<>();
        imgIds.add(1L);
        imgIds.add(2L);

        Long userId = 1L;

        List<Image> images = new ArrayList<>();
        Image image = new Image();
        image.setId(1L);
        image.setImgName("test");
        images.add(image);

        List<String> objectNames = new ArrayList<>();
        objectNames.add("test");

        // Mock imageGateway.deleteImgByImgIds()
        Mockito.when(imageGateway.deleteImgByImgIds(Mockito.anyList()))
                .thenReturn(true);
        // Mock imageGateway.selectObjetNameByIds()
        Mockito.when(imageGateway.selectObjetNameByIds(Mockito.anyList(), Mockito.anyLong()))
                .thenReturn(images);

        // Mock imageGateway.deleteImgMinio()
        Mockito.when(imageGateway.deleteImgMinio(Mockito.anyList()))
                .thenReturn(objectNames);

        // Mock imageGateway.deleteImgDb()
        Mockito.when(imageGateway.deleteImgDb(Mockito.anyList(), Mockito.anyLong()))
                .thenReturn(true);

        // when
        boolean result = underTest.deleteImg(imgIds, userId);

        // then
        ArgumentCaptor<List<Long>> imgIdsCaptor = ArgumentCaptor.forClass(List.class);
        ArgumentCaptor<Long> userIdCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<List<String>> objectNamesCaptor = ArgumentCaptor.forClass(List.class);
        assertThat(result).isTrue();
        Mockito.verify(imageGateway).deleteImgByImgIds(imgIdsCaptor.capture());
        assertThat(imgIdsCaptor.getValue()).isEqualTo(imgIds);

        Mockito.verify(imageGateway).selectObjetNameByIds(imgIdsCaptor.capture(), userIdCaptor.capture());
        assertThat(imgIdsCaptor.getValue()).isEqualTo(imgIds);
        assertThat(userIdCaptor.getValue()).isEqualTo(userId);

        Mockito.verify(imageGateway).deleteImgMinio(objectNamesCaptor.capture());
        assertThat(objectNamesCaptor.getValue()).isEqualTo(objectNames);

        Mockito.verify(imageGateway).deleteImgDb(imgIdsCaptor.capture(), userIdCaptor.capture());
        assertThat(imgIdsCaptor.getValue()).isEqualTo(imgIds);
        assertThat(userIdCaptor.getValue()).isEqualTo(userId);

        Mockito.verifyNoMoreInteractions(imageGateway);
    }

    @Test
    void getImgList() {
    }

    @Test
    void deleteImacgeclass() {
    }

    @Test
    void moveImageclass() {
    }
}