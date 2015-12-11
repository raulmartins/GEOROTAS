package rmtech.georotas;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by raullima on 11/12/15.
 */
public class FragmentScanner extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View scannerView = inflater.inflate(R.layout.fragment_scanner,container,false);
        Button btn = (Button) scannerView.findViewById(R.id.btn_scannear);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scannearDocumento(v);
            }
        });
        return scannerView;
    }
    public void scannearDocumento (View view){
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivityForResult(intent,0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data != null){
            Bundle bundle = data.getExtras();
            if(bundle !=null){
                Bitmap img = (Bitmap) bundle.get("data");
                ImageView imgdoc = (ImageView) getView().findViewById(R.id.img_documento);
                imgdoc.setImageBitmap(img);
            }
        }
    }

}
