package io.keweishang.network.uri;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * 1. URI is more general. URL is a type of URI.
 * 2. Only a small collection of schemas can be qualified as URL, such as http, https, ftp, sftp, etc.
 */
public class UriUrl {
    public static void main(String[] args) throws URISyntaxException, IOException {
//        no exception
//        System.out.println(createUri());

//        throw java.net.MalformedURLException: unknown protocol: otherprotocol
//        System.out.println(createUrl());

//        create a remote connection from URL and retrieve content remotely
//        System.out.println(openRemoteConnection(new URL("http://google.com")));
    }

    public static URI createUri() throws URISyntaxException {
        return new URI("somescheme://theuser:thepassword@someauthority:80/some/path?thequery#somefragment");
    }

    public static URL createUrl() throws MalformedURLException {
        return new URL("otherprotocol://somehost/path/to/file");
    }

    public static String openRemoteConnection(URL url) throws IOException {
        return IOUtils.toString(url.openStream());
    }
}
