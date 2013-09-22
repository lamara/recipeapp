package com.mhacks.recipe;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by Alex on 9/22/13.
 */
public class RecipeAdapter extends ArrayAdapter<String> {

    private Context context;
    private String[] values;
    public RecipeAdapter(Context context, String[] values) {
        super(context, R.layout.list_row, values);

        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.list_row, parent, false);

        TextView instruction = (TextView) rowView.findViewById(R.id.instruction);
        final TextView timer = (TextView) rowView.findViewById(R.id.timer);
        TextView step = (TextView) rowView.findViewById(R.id.step);
        instruction.setText(values[position]);
        // Change the icon for Windows and iPhone
        String string = values[position];

        step.setText(String.valueOf(position + 1));
        instruction.setText(string);

        final int seconds = detectTime(string);
        if (seconds != -1) {
            timer.setVisibility(View.VISIBLE);
            timer.setText(String.format("%01d:%02d:%02d", seconds / (3600), ((seconds) % 3600) / (60), (seconds) % 60));

            timer.setOnClickListener(new View.OnClickListener() {
                private boolean isRunning = false;

                @Override
                public void onClick(View view) {
                    if (isRunning) {
                        return;
                    }
                    setAlarm(seconds);
                    new CountDownTimer(seconds * 1000, 1000) {
                        public void onTick(long millisUntilFinished) {
                            String time = String.format("%01d:%02d:%02d", millisUntilFinished / 1000 / (3600),
                                    ((millisUntilFinished / 1000) % 3600) / (60), ((millisUntilFinished / 1000) % 60));
                            timer.setText(time);
                        }

                        public void onFinish() {
                            timer.setText(String.format("%01d:%02d:%02d", 0, 0, 0));
                        }
                    }.start();
                    isRunning = true;
                }
            });
        }
        return rowView;
    }

    private void setAlarm(int secondsToAlarm) {
        Intent intent = new Intent(context, AlarmReceiverActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 12222, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (secondsToAlarm * 1000), pendingIntent);

    }

    /**
     * detects whether a timer should be set for
     * @param input
     * @return
     */
    private int detectTime(String input) {
        String[] delimitedInput = input.split(" ");
        for (int i = delimitedInput.length - 1; i > 0; i--) {
            String word = delimitedInput[i];
            if (word.equals("minute")) {
                return 60;
            }
            if (word.equals("hour")) {
                return 60 * 60;
            }
            if (word.equals("minutes")) {
                //check if the words preceeding it are quantites.
                String preceedingWord = delimitedInput[i - 1];
                try {
                    return Integer.parseInt(preceedingWord) * 60;
                }
                catch (NumberFormatException e) {
                    if (preceedingWord.equals("few") || preceedingWord.equals("couple")) {
                        return 2 * 60;
                    }
                }
            }
            if (word.equals("hours")) {
                String preceedingWord = delimitedInput[i - 1];
                try {
                    return Integer.parseInt(preceedingWord) * 60 * 60;
                }
                catch (NumberFormatException e) {
                    if (preceedingWord.equals("few") || preceedingWord.equals("couple")) {
                        return 2 * 60 * 60;
                    }
                }
            }
          }
        return -1;
    }

}
