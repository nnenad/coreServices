
package com.java.web.core.lib.test;

import java.io.IOException;

public class RedirectException extends IOException {

    public RedirectException(String url) {
        super(url);
    }

}
