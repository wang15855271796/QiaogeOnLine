package com.puyue.www.qiaoge.view;

import android.content.Context;
import android.provider.ContactsContract;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;

import java.io.InputStream;

/**
 * Created by ${王文博} on 2019/4/15
 */
@GlideModule
public class GlideConfiguration extends AppGlideModule {
    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        super.applyOptions(context, builder);
        builder.setDefaultRequestOptions(new RequestOptions().format(DecodeFormat.PREFER_ARGB_8888));
    }

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide,
                                   @NonNull Registry registry) {
//        registry.append(ContactsContract.Contacts.Photo.class, InputStream.class, new FlickrModelLoader.Factory());
    }


    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}
