package com.gilxyj.netty.attribute;

import com.gilxyj.netty.session.Session;
import io.netty.util.AttributeKey;

public interface Attributes {

    AttributeKey<Session> SESSION = AttributeKey.newInstance("session");
}
