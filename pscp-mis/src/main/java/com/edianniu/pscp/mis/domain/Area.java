/**
 *
 */
package com.edianniu.pscp.mis.domain;

import java.io.Serializable;

/**
 * @author cyl
 */
public class Area extends BaseDo implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String name;
    private String latitude;
    private String longitude;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
