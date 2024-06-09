package com.example.bitemate;

import static com.google.firebase.appcheck.internal.util.Logger.TAG;

import android.app.Activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Handler;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DiffUtil;

import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.StackFrom;
import com.yuyakaido.android.cardstackview.SwipeableMethod;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {


        private static final String TAG = "MainActivity";
        private CardStackLayoutManager manager;
        private CardStackAdapter adapter;

        @Override
        protected  void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            CardStackView cardStackView = findViewById(R.id.card_stack_view);

            manager = new CardStackLayoutManager(this, new CardStackListener() {
                @Override
                public void onCardDragging(Direction direction, float ratio) {
                    Log.d(TAG,"onCardDragging: d=" + direction.name() + "ratio=" + ratio);
                }

                @Override
                public void onCardSwiped(Direction direction) {
                Log.d(TAG,"onCardSwiped: p=" + manager.getTopPosition() + "d=" + direction);
                if(direction == Direction.Right){
                    showHeartImage();
                }
                if(direction == Direction.Top){
                    showHeartImage();
                }
                if (direction == Direction.Left){
                    showXImage();
                }
                if (direction == Direction.Bottom){
                    showXImage();
                }

                //paginating
                    if (manager.getTopPosition() == adapter.getItemCount() - 1){
                        manager.setTopPosition(0);
                        adapter.notifyDataSetChanged();
                    } else if (manager.getTopPosition() == adapter.getItemCount() - 5) {
                        paginate();
                    }
                }

                @Override
                public void onCardRewound() {
                    Log.d(TAG,"onCardRewounds : " + manager.getTopPosition());

                }

                @Override
                public void onCardCanceled() {
                    Log.d(TAG,"onCardRewounds : " + manager.getTopPosition());
                }

                @Override
                public void onCardAppeared(View view, int position) {
                    TextView tv = view.findViewById(R.id.item_name);
                    Log.d(TAG,"onCardAppeared : " + position + ", name; " + tv.getText());
                }

                @Override
                public void onCardDisappeared(View view, int position) {
                    TextView tv = view.findViewById(R.id.item_name);
                    Log.d(TAG,"onCardAppeared : " + position + ", name; " + tv.getText());
                }
            });
            manager.setStackFrom(StackFrom.None);
            manager.setVisibleCount(3);
            manager.setTranslationInterval(8.0f);
            manager.setScaleInterval(0.95f);
            manager.setSwipeThreshold(0.3f);
            manager.setMaxDegree(20.0f);
            manager.setDirections(Direction.FREEDOM);
            manager.setCanScrollHorizontal(true);
            manager.setSwipeableMethod(SwipeableMethod.Manual);
            manager.setOverlayInterpolator(new LinearInterpolator());
            adapter = new CardStackAdapter(addList());
            cardStackView.setLayoutManager(manager);
            cardStackView.setAdapter(adapter);
            cardStackView.setItemAnimator(new DefaultItemAnimator());
        }



    private void paginate() {
        List<ItemModel> old = adapter.getItems();
        List<ItemModel> newest = new ArrayList<>(addList());
        CardStackCallBack callback  = new CardStackCallBack(old,newest);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);
        adapter.setItems(newest);
        result.dispatchUpdatesTo(adapter);
    }

    private List<ItemModel> addList() {
            List<ItemModel> items = new ArrayList<>();
        items.add(new ItemModel(R.drawable.card7,"Seek Kabab" ,"Restaurant: Sizzle Spot", "Location: Belapur"));
        items.add(new ItemModel(R.drawable.card1,"Pan Cakes" ,"Restaurant: 99 PanCakes", "Location: Kharghar"));
        items.add(new ItemModel(R.drawable.card2,"Caesar Salad" ,"Restaurant: Courtyard", "Location: Kharghar"));
        items.add(new ItemModel(R.drawable.card3,"Fried Egg Rice" ,"Restaurant: Gourmet Kitchen", "Location: Panvel"));
        items.add(new ItemModel(R.drawable.card4,"Chicken Subway" ,"Restaurant: Epicurean Symphony", "Location: Nerul"));
        items.add(new ItemModel(R.drawable.card17,"Vada Pav" ,"Restaurant: VadaPav Wala", "Location: Vashi"));
        items.add(new ItemModel(R.drawable.card5,"Courtyard Pavilion" ,"Restaurant: Namaste Nirvana Bistro", "Location: Seawoods"));
        items.add(new ItemModel(R.drawable.card6,"Chicken Burger" ,"Restaurant: Snap Crave", "Location: Kurla"));
        items.add(new ItemModel(R.drawable.card8,"Lolipop" ,"Restaurant: Crisp Crave", "Location: Kharghar"));
        items.add(new ItemModel(R.drawable.card9,"Chocolate Cheesecake" ,"Restaurant: Sizzle Spot", "Location: Belapur"));
        items.add(new ItemModel(R.drawable.card10,"Ramen" ,"Restaurant: Munch Mix", "Location: Pain"));
        items.add(new ItemModel(R.drawable.card11,"Gourment Pizza" ,"Restaurant: Sizzle Spot", "Location: Pune"));
        items.add(new ItemModel(R.drawable.card12,"Brownie" ,"Restaurant: HighWay Break", "Location: Pune"));
        items.add(new ItemModel(R.drawable.card13,"Delight Donuts" ,"Restaurant: Shree Hotel", "Location: Kharghar"));
        items.add(new ItemModel(R.drawable.card14,"BBQ Prawns" ,"Restaurant: Courtyard", "Location: Panvel"));
        items.add(new ItemModel(R.drawable.card16,"Berry Pie" ,"Restaurant: Benny's Kitchen", "Location: Seawoods"));
        items.add(new ItemModel(R.drawable.card15,"BlueBerry Yogurt" ,"Restaurant: Rider Break", "Location: Nerul"));
        items.add(new ItemModel(R.drawable.card18,"Lobster" ,"Restaurant: Rider Break", "Location: Panvel"));
        items.add(new ItemModel(R.drawable.card19,"Vanilla Croissant" ,"Restaurant: Bakers Factory", "Location: Panvel"));
        items.add(new ItemModel(R.drawable.card20,"Sushi" ,"Restaurant: Pronto", "Location: Kharghar"));
        items.add(new ItemModel(R.drawable.card21,"Almond Shake" ,"Restaurant: Bikaneer", "Location: Satara"));
        items.add(new ItemModel(R.drawable.card22,"Olive Pizza" ,"Restaurant: Pizzaria", "Location: Pune"));
        items.add(new ItemModel(R.drawable.card23,"Butta Curry" ,"Restaurant: Mumbai Corner", "Location: Aroli"));
        items.add(new ItemModel(R.drawable.card24,"CupCake" ,"Restaurant: 7th Heaven", "Location: Belapur"));
        items.add(new ItemModel(R.drawable.card25,"French Fries" ,"Restaurant: Cafe Cream", "Location: Khandeshwar"));
        items.add(new ItemModel(R.drawable.card26,"Chocolate Cookie" ,"Restaurant: Munch Mix", "Location: Mumbai Central"));
        items.add(new ItemModel(R.drawable.card27,"Dumpling" ,"Restaurant: Pronto", "Location: Nerul"));
        items.add(new ItemModel(R.drawable.card28,"Chicken Biryani" ,"Restaurant: Highway Break", "Location: Chembur"));
        items.add(new ItemModel(R.drawable.card29,"Veg Burito" ,"Restaurant: Oho Shwarama", "Location: Biwandi"));
        items.add(new ItemModel(R.drawable.card30,"Hot Dog" ,"Restaurant: Mumbai Street Food Corner", "Location: Dadar"));
        items.add(new ItemModel(R.drawable.card31,"Samosa" ,"Restaurant: Bhawati Food", "Location: Lonavla"));
        items.add(new ItemModel(R.drawable.card32,"Daal Rice" ,"Restaurant: Mumbai Corner", "Location: Uran"));
        items.add(new ItemModel(R.drawable.card33,"Idli" ,"Restaurant: Anna Dhaba", "Location: Pavel"));
        items.add(new ItemModel(R.drawable.card34,"Masala Dosa" ,"Restaurant: Anna Dhaba", "Location: Mansaovar"));
        items.add(new ItemModel(R.drawable.card35,"Pav Bhaji" ,"Restaurant: Sizzle Spot", "Location: Kalamboli"));
        items.add(new ItemModel(R.drawable.card36,"Tomato Pasta" ,"Restaurant: Sizzle Spot", "Location: Belpada"));
        items.add(new ItemModel(R.drawable.card37,"Chilli Fries" ,"Restaurant: Cafe Cream", "Location: Nerul"));
        items.add(new ItemModel(R.drawable.card38,"Cinnamon Tea" ,"Restaurant: ChaiWala", "Location: Kharghar"));
        items.add(new ItemModel(R.drawable.card39,"Fries Fish" ,"Restaurant: South Fish", "Location: Vashi"));
            return items;
    }
    private void showHeartImage() {
        final ImageView heartImageView = findViewById(R.id.heartImageView);
        if (heartImageView == null) {
            Log.e(TAG, "heartImageView is null!");
            return;
        }

        // Set the visibility of the heart image to VISIBLE
        heartImageView.setVisibility(View.VISIBLE);

        // Use a Handler to delay hiding the heart image after 0.5 seconds
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Set the visibility of the heart image back to GONE after the delay
                heartImageView.setVisibility(View.GONE);
            }
        }, 500); // 500 milliseconds (0.5 seconds) delay
    }

    private void showXImage() {
        final ImageView xImageView = findViewById(R.id.cross);
        if (xImageView == null) {
            Log.e(TAG, "xImageView is null!");
            return;
        }

        // Set the visibility of the "x" image to VISIBLE
        xImageView.setVisibility(View.VISIBLE);

        // Use a Handler to delay hiding the "x" image after 0.5 seconds
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Set the visibility of the "x" image back to GONE after the delay
                xImageView.setVisibility(View.GONE);
            }
        }, 500); // 500 milliseconds (0.5 seconds) delay
    }




}
