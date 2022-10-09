package edu.northeastern.numad22fa_yaozhengwang.primeDirective;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import edu.northeastern.numad22fa_yaozhengwang.R;

public class PrimeDirectiveActivity extends AppCompatActivity {

    private int currentNum = 3;
    private int foundPrime = 3;
    private boolean find = false;
    private boolean terminate = false;
    private boolean pacifierSwitch = false;
    private final Handler primeTextHandler = new Handler();
    private TextView currentNumberText;
    private TextView lastPrimeNumberText;
    private static final String TAG = "PrimeDirectiveActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prime_directive);
        currentNumberText = findViewById(R.id.currentNumber);
        lastPrimeNumberText = findViewById(R.id.lastPrimeNumber);
        Button findPrimes = findViewById(R.id.findPrimes);
        findPrimes.setOnClickListener(l -> {
            find = true;
            terminate = false;
            runFindPrimesThread();
        });
        Button terminateSearch = findViewById(R.id.terminateSearch);
        terminateSearch.setOnClickListener(l -> {
            terminate = true;
            find = false;
        });
        CheckBox pacifierSwitchCheckBox = findViewById(R.id.pacifierSwitch);
        pacifierSwitch = pacifierSwitchCheckBox.isChecked();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("currentNum", currentNum);
        outState.putInt("foundPrime", foundPrime);
        outState.putBoolean("terminate", terminate);
        outState.putBoolean("pacifierSwitch", pacifierSwitch);
        outState.putBoolean("find", find);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        currentNum = savedInstanceState.getInt("currentNum");
        foundPrime = savedInstanceState.getInt("foundPrime");
        terminate = savedInstanceState.getBoolean("terminate");
        pacifierSwitch = savedInstanceState.getBoolean("pacifierSwitch");
        find = savedInstanceState.getBoolean("find");
        currentNumberText.setText(new StringBuilder(
                "Now the number being searched is " + currentNum));
        lastPrimeNumberText.setText(new StringBuilder(
                "The last prime number searched is " + foundPrime));
        CheckBox checkBox = findViewById(R.id.pacifierSwitch);
        checkBox.setChecked(pacifierSwitch);
        if (find) {
            runFindPrimesThread();
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("Do you want to exit?");
        alertDialog.setNegativeButton("No", (dialog, which) -> dialog.cancel());
        alertDialog.setPositiveButton("Yes", (dialog, which) -> finish());
        alertDialog.create().show();
    }

    private void runFindPrimesThread() {
        FindPrimesThread findPrimesThread = new FindPrimesThread();
        new Thread(findPrimesThread).start();
    }

    class FindPrimesThread implements Runnable {

        @Override
        public void run() {
            int increment = 2;
            int loop = 0;
            while (!terminate) {
                currentNum += increment;
                loop ++;
                primeTextHandler.post(() -> {
                    currentNumberText.setText(new StringBuilder(
                            "Now the number being searched is " + currentNum));

                    if(isPrime(currentNum)) {
                        foundPrime = currentNum;
                        lastPrimeNumberText.setText(new StringBuilder(
                                "The last prime number searched is " + foundPrime));
                    }
                });
                Log.d(TAG, "Running a thread : " + loop);
                try {
                    Thread.sleep(400);
                } catch (InterruptedException e) {
                    Log.d("ERROR Interrupted:", "Interrupted!");
                    e.printStackTrace();
                }
            }
        }

        private boolean isPrime(int num) {
            for (int i = 2; i < num; i++) {
                if(num % i == 0) return false;
            }
            return true;
        }
    }
}
