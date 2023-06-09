// Generated by view binder compiler. Do not edit!
package com.example.chatting_program.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.chatting_program.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityUpupBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final CheckedTextView btConnect;

  @NonNull
  public final Button btSend;

  @NonNull
  public final ConstraintLayout ctlParent;

  @NonNull
  public final EditText etInput;

  @NonNull
  public final ImageView imageView;

  @NonNull
  public final TextView line;

  @NonNull
  public final TextView textView3;

  @NonNull
  public final TextView textView4;

  @NonNull
  public final TextView textView5;

  @NonNull
  public final TextView title;

  @NonNull
  public final TextView tvChat;

  @NonNull
  public final TextView tvImage;

  @NonNull
  public final TextView tvIntroduce;

  @NonNull
  public final TextView tvMessage;

  private ActivityUpupBinding(@NonNull ConstraintLayout rootView,
      @NonNull CheckedTextView btConnect, @NonNull Button btSend,
      @NonNull ConstraintLayout ctlParent, @NonNull EditText etInput, @NonNull ImageView imageView,
      @NonNull TextView line, @NonNull TextView textView3, @NonNull TextView textView4,
      @NonNull TextView textView5, @NonNull TextView title, @NonNull TextView tvChat,
      @NonNull TextView tvImage, @NonNull TextView tvIntroduce, @NonNull TextView tvMessage) {
    this.rootView = rootView;
    this.btConnect = btConnect;
    this.btSend = btSend;
    this.ctlParent = ctlParent;
    this.etInput = etInput;
    this.imageView = imageView;
    this.line = line;
    this.textView3 = textView3;
    this.textView4 = textView4;
    this.textView5 = textView5;
    this.title = title;
    this.tvChat = tvChat;
    this.tvImage = tvImage;
    this.tvIntroduce = tvIntroduce;
    this.tvMessage = tvMessage;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityUpupBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityUpupBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_upup, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityUpupBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.bt_connect;
      CheckedTextView btConnect = ViewBindings.findChildViewById(rootView, id);
      if (btConnect == null) {
        break missingId;
      }

      id = R.id.bt_send;
      Button btSend = ViewBindings.findChildViewById(rootView, id);
      if (btSend == null) {
        break missingId;
      }

      id = R.id.ctl_parent;
      ConstraintLayout ctlParent = ViewBindings.findChildViewById(rootView, id);
      if (ctlParent == null) {
        break missingId;
      }

      id = R.id.et_input;
      EditText etInput = ViewBindings.findChildViewById(rootView, id);
      if (etInput == null) {
        break missingId;
      }

      id = R.id.imageView;
      ImageView imageView = ViewBindings.findChildViewById(rootView, id);
      if (imageView == null) {
        break missingId;
      }

      id = R.id.line;
      TextView line = ViewBindings.findChildViewById(rootView, id);
      if (line == null) {
        break missingId;
      }

      id = R.id.textView3;
      TextView textView3 = ViewBindings.findChildViewById(rootView, id);
      if (textView3 == null) {
        break missingId;
      }

      id = R.id.textView4;
      TextView textView4 = ViewBindings.findChildViewById(rootView, id);
      if (textView4 == null) {
        break missingId;
      }

      id = R.id.textView5;
      TextView textView5 = ViewBindings.findChildViewById(rootView, id);
      if (textView5 == null) {
        break missingId;
      }

      id = R.id.title;
      TextView title = ViewBindings.findChildViewById(rootView, id);
      if (title == null) {
        break missingId;
      }

      id = R.id.tv_chat;
      TextView tvChat = ViewBindings.findChildViewById(rootView, id);
      if (tvChat == null) {
        break missingId;
      }

      id = R.id.tv_image;
      TextView tvImage = ViewBindings.findChildViewById(rootView, id);
      if (tvImage == null) {
        break missingId;
      }

      id = R.id.tv_introduce;
      TextView tvIntroduce = ViewBindings.findChildViewById(rootView, id);
      if (tvIntroduce == null) {
        break missingId;
      }

      id = R.id.tv_message;
      TextView tvMessage = ViewBindings.findChildViewById(rootView, id);
      if (tvMessage == null) {
        break missingId;
      }

      return new ActivityUpupBinding((ConstraintLayout) rootView, btConnect, btSend, ctlParent,
          etInput, imageView, line, textView3, textView4, textView5, title, tvChat, tvImage,
          tvIntroduce, tvMessage);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
