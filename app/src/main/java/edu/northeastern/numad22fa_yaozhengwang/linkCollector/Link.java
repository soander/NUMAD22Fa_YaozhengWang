package edu.northeastern.numad22fa_yaozhengwang.linkCollector;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Patterns;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;


public class Link {
    private final String linkName;
    private final String linkUrl;

    public Link(String linkName, String linkUrl) {
        this.linkName = linkName;
        this.linkUrl = linkUrl;
    }

    public void launchUrl(Context context) {
        Intent browserIntent  = new Intent(Intent.ACTION_VIEW, Uri.parse(linkUrl));
        context.startActivity(browserIntent);
    }

    public String getLinkName() {
        return linkName;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public boolean checkLink() {
        try {
            new URL(linkUrl).toURI();
        } catch (MalformedURLException | URISyntaxException e) {
            return false;
        }
        return Patterns.WEB_URL.matcher(linkUrl).matches();
    }
}
