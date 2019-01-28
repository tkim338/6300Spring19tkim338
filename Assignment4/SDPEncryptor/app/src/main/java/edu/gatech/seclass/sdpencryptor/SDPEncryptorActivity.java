package edu.gatech.seclass.sdpencryptor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;
import java.util.ArrayList;

public class SDPEncryptorActivity extends AppCompatActivity {

    static String Ciphertext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView messageTextView = findViewById(R.id.messageText);
        final TextView rotateTextView = findViewById(R.id.rotateNumber);
        final TextView shiftTextView = findViewById(R.id.shiftNumber);
        messageTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean hasLetter = false;
                for (int e = 0; e < s.length(); e++) {
                    if (Character.isLetter(s.charAt(e))) {
                        hasLetter = true;
                    }
                }
                if (!hasLetter) {
                    messageTextView.setError("Alphabetic Message Required");
                    TextView messageOutputView = findViewById(R.id.resultText);
                    messageOutputView.setText("");
                }
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });
      /*  shiftTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if ((s.length()==0 || Integer.parseInt(s.toString())==0) && (rotateTextView.getText().length() == 0 || Integer.parseInt(rotateTextView.getText().toString())==0)) {
                    if (rotateTextView.getText().length() == 0) {
                        shiftTextView.setError("No Encryption Applied");
                        TextView messageOutputView = findViewById(R.id.resultText);
                        messageOutputView.setText("");
                    }
                }
                else {
                    int shiftInt = Integer.parseInt(s.toString());
                    if (shiftInt < 0 || shiftInt > 25) {
                        shiftTextView.setError("Must Be Between 0 And 25");
                        TextView messageOutputView = findViewById(R.id.resultText);
                        messageOutputView.setText("");
                    }
                }
            }
            @Override
            public void afterTextChanged(Editable s) { }
        }); */
       /* rotateTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if ((s.length()==0 || Integer.parseInt(s.toString())==0) && (shiftTextView.getText().length() == 0 || Integer.parseInt(shiftTextView.getText().toString())==0)) {
                    rotateTextView.setError("No Encryption Applied");
                    TextView messageOutputView = findViewById(R.id.resultText);
                    messageOutputView.setText("");
                }
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });*/
    }

    // encrypt message
    public void encryptMessage(View view) {
        ArrayList<Integer> charIndex = new ArrayList<>();
        Ciphertext = "";
        TextView messageTextView = findViewById(R.id.messageText);
        String inputMessage = messageTextView.getText().toString();
        TextView rotateTextView = findViewById(R.id.rotateNumber);
        TextView shiftTextView = findViewById(R.id.shiftNumber);

        if ((shiftTextView.getText().length() == 0 || Integer.parseInt(shiftTextView.getText().toString()) == 0)
                && (rotateTextView.getText().length() == 0 || Integer.parseInt(rotateTextView.getText().toString()) == 0)) {
            shiftTextView.setError("No Encryption Applied");
            rotateTextView.setError("No Encryption Applied");
            TextView messageOutputView = findViewById(R.id.resultText);
            messageOutputView.setText("");
        } else {
            int rotateInt = Integer.parseInt(rotateTextView.getText().toString());
            int shiftInt = Integer.parseInt(shiftTextView.getText().toString());

            rotateTextView.setError(null);
            if (shiftInt < 0 || shiftInt > 25) {
                shiftTextView.setError("Must Be Between 0 And 25");
                TextView messageOutputView = findViewById(R.id.resultText);
                messageOutputView.setText("");
            }
            else {
                shiftTextView.setError(null);
            }

            int messageLength = inputMessage.length();
            for (int i = 0; i < messageLength; i++) {
                charIndex.add((i + messageLength - (rotateInt%messageLength)) % messageLength);

                int charAscii = (int) inputMessage.charAt(i);
                String shiftedChar;
                int ascii_A = 65;
                int ascii_a = 97;
                int shiftFactor;
                if (charAscii >= ascii_a) { //lowercase
                    shiftFactor = ascii_a - 1;
                } else {
                    shiftFactor = ascii_A - 1;
                }
                if (Character.isLetter(inputMessage.charAt(i))) {
                    shiftedChar = Character.toString((char) (((((charAscii - shiftFactor) + shiftInt) - 1) % 26) + 1 + shiftFactor));
                } else {
                    shiftedChar = inputMessage.substring(i, i + 1);
                }
                //Ciphertext = Ciphertext + String.valueOf(charAscii) + " " + String.valueOf((charAscii + shiftInt - shiftFactor)%26 + shiftFactor);
                Ciphertext = Ciphertext + String.valueOf(shiftedChar);
            }

            String Ciphertext2 = "";
            for (int j = 0; j < messageLength; j++) {
                Ciphertext2 = Ciphertext2 + Ciphertext.substring((Integer) charIndex.get(j), (Integer) charIndex.get(j) + 1);
            }

            //Ciphertext = inputMessage;
            TextView messageOutputView = findViewById(R.id.resultText);
            messageOutputView.setText(Ciphertext2);
        }
    }
}
