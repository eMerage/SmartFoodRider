package emerge.project.onmealrider.ui.font;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by attract on 3/12/15.
 */

public class OpenSansLightEditText extends EditText {
    public OpenSansLightEditText(Context context) {
        super(context);
        init();
    }

    public OpenSansLightEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public OpenSansLightEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "Font/OpenSans-Light.ttf");
        setTypeface(tf);
    }
}
