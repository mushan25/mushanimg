package com.hzb.file.domain;

import com.hzb.file.domain.image.model.entities.Image;

/**
 * @author: hzb
 * @Date: 2023/5/5
 */
public class DomainFactory {
    public static Image getImage(){
        return new Image();
    }
}
