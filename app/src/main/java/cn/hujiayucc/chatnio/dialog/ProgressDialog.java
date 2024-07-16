package cn.hujiayucc.chatnio.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import cn.hujiayucc.chatnio.databinding.AlertProgressBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import org.jetbrains.annotations.NotNull;

public class ProgressDialog extends MaterialAlertDialogBuilder {
    private final AlertProgressBinding binding;
    @SuppressLint("UseCompatLoadingForDrawables")
    public ProgressDialog(Context context, LayoutInflater layoutInflater) {
        super(context);
        binding = AlertProgressBinding.inflate(layoutInflater);
        setView(binding.getRoot());
    }


    @NotNull
    @Override
    public MaterialAlertDialogBuilder setTitle(CharSequence title) {
        binding.title.setText(title);
        return this;
    }
}
