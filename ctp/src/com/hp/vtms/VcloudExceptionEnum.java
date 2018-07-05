package com.hp.vtms;

/**
 * Created with IntelliJ IDEA.
 * User: meij
 * Date: 14-3-20
 * Time: PM2:59
 * To change this template use File | Settings | File Templates.
 */
public enum VcloudExceptionEnum {
    _204_NO_CONTENT("204", "There is no content returned."),
    _302_MOVED_TEMPORARILY("302", "Requested resource is moved temporarily."),
    _400_BAD_REQUEST("400", "Your request is invalid."),
    _401_UNAUTHORIZED("401", "Your request is unauthorized."),
    _403_FORBIDDEN("403", "You are forbidden from vCloud server."),
    _404_NOT_FOUND("404", "No file is found on vCloud server."),
    _405_METHOD_NOT_ALLOWED("405", "Your HTTP request is not supported."),
    _406_NOT_ACCEPTABLE("406", "Note: I'm confused that what is not acceptable? "),
    _409_CONFLICT("409", "There is conflict between local environment and vCloud server."),
    _415_UNSUPPORTED_MEDIA_TYPE("415", "The media type is not supported."),
    _500_INTERNAL_SERVER_ERROR("500", "vCloud server is down."),
    _504_GATEWAY_TIMEOUT("504", "vCloud server is time out."),
    _601_VCLOUD_NOT_ASSIGNED("601", "There is no vCloud server assigned to this course."),
    _602_DATABASE_NOT_EVENT("602", "Internal Server Error!"),
    _603_SFTP_CONNECT_ERR("603", "You don't have course in database.");

    public String code;
    public String errStr;
    private static final String VCLOUD = "Server temporary unavailable: ";

    VcloudExceptionEnum(String _code, String _errStr) {
        this.code = _code;
        this.errStr = VCLOUD + _errStr;
    }
}
