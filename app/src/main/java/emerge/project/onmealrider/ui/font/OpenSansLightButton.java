package emerge.project.onmealrider.ui.font;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by attract on 3/12/15.
 */

public class OpenSansLightButton extends Button {
    public OpenSansLightButton(Context context) {
        super(context);
        init();
    }

    public OpenSansLightButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public OpenSansLightButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "Font/OpenSans-Light.ttf");
        setTypeface(tf);
    }
}
