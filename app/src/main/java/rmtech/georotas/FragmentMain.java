package rmtech.georotas;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by raullima on 11/12/15.
 */
public class FragmentMain extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View codigoBarras = inflater.inflate(R.layout.content_georotas,container,false);
        return codigoBarras;
    }
}
