package com.example.flyhigh.drawable_engine;

import android.content.Context;

import androidx.annotation.ColorRes;

public class MenuItemRecolorer {

    @ColorRes
    private int activeColor;
    @ColorRes
    private int restingColor;
    private int oldPosition = 0;
    private DrawableBank[] drawableBank;
    private DrawableHelper helper;

    /**To use inside the ViewPager.onPageChangeListener() inside the onPageSelected(int position)
     * The class should be initialized as follows:
     *
     * private MenuItemRecolorer recolor = new MenuItemRecolorer(this);
     *
     * Upon initialization, a drawable bank with the drawables that compose the Menu should be declared:
     *
     * final DrawableBank[] drawableBank = {
     *                 new DrawableBank(R.drawable.circle, binding.imageButtonA),
     *                 new DrawableBank(R.drawable.square, binding.imageButtonB),
     *                 new DrawableBank(R.drawable.triangle, binding.imageButtonC),
     *                 new DrawableBank(R.drawable.squared_circle, binding.imageButtonD)
     *         };
     *
     * This class is composed of two parts:
     * the drawable (a Drawable Vector Asset)that will be recolored, and the destination (in this case a button) in which the drawable will be reinserted.
     *
     * The order of the constructions should match the number of the page assigned to the button.
     *
     * Here is an example of how the button that redirects to page 0 looks:
     *
     *             <ImageButton
     *                 android:id="@+id/imageButtonA"
     *                 //code
     *                 android:src="@drawable/circle" />
     * ----------------------------------------------------
     *     protected void onCreate(Bundle savedInstanceState)
     *          binding.imageButtonA.setOnClickListener(this);
     * ----------------------------------------------------
     *     public void onClick(View v)
     *         if (id == R.id.imageButtonA)
     *             getMyViewPager().setCurrentItem(0, true);
     *
     *
     * Given that this button has a circle as drawable and redirect to page 0, its DrawableBank constructor should
     * have a drawable circle, assigned to the imageButton with Id imageButtonA, and placed as the first item in the Array.
     *
     * Now that the bank is constructed, the MenuItemRecolorer item should be given the desired values.
     *
     *         recolor
     *                 .recolorBank(drawableBank)........................the bank.
     *                 .fromPassiveColor(R.color.colorLightDark).........the color of the button when the page they redirect to, is not displayed.
     *                 .towardsActiveColor(R.color.white);...............the color of the button of the active(current) page
     *
     * Finally:
     *
     * public void onPageSelected(int position)
     *
     *      recolor.accordingTo(position);
     * */


    /**
     * 1)
     */
    public MenuItemRecolorer(Context context) {
        this.helper = new DrawableHelper(context);
    }


    /**
     * 2)
     */
    public MenuItemRecolorer recolorBank(DrawableBank[] bank) {
        this.drawableBank = bank;
        return this;
    }

    /**
     * 3)
     */
    public MenuItemRecolorer fromPassiveColor(@ColorRes int restingColor) {
        this.restingColor = restingColor;
        return this;
    }

    /**
     * 4)
     */
    public MenuItemRecolorer towardsActiveColor(@ColorRes int activeColor) {
        this.activeColor = activeColor;
        return this;
    }

    /**
     * 5)
     * */
    public void accordingTo (int newPosition) {
        if (newPosition != oldPosition) {
            /**Active Color*/
            helper.recolorRes(activeColor, drawableBank[newPosition]);
            /**Deactive Color*/
            helper.recolorRes(restingColor, drawableBank[oldPosition]);
            oldPosition = newPosition;

        }
    }


}
