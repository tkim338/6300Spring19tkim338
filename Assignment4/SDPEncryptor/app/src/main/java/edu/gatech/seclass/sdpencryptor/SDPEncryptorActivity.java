package edu.gatech.seclass.sdpencryptor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SDPEncryptorActivity extends AppCompatActivity {

    static String Ciphertext = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // encrypt message
    public void encryptMessage(View view) {
        TextView messageTextView = findViewById(R.id.messageText);
        String inputMessage = messageTextView.getText().toString();
        TextView rotateTextView = findViewById(R.id.rotateNumber);
        int rotateInt = Integer.parseInt(rotateTextView.getText().toString());
        TextView shiftTextView = findViewById(R.id.shiftNumber);
        int shiftInt = Integer.parseInt(shiftTextView.getText().toString());

        int messageLength = inputMessage.length();
        for (int i = 0; i < messageLength; i++) {
            int charAscii = (int) inputMessage.charAt(i);
            String shiftedChar;
            int ascii_A = 65;
            int ascii_a = 97;
            int shiftFactor;
            if (charAscii >= ascii_a) { //lowercase
                shiftFactor = ascii_a + 1;
            }
            else {
                shiftFactor = ascii_A + 1;
            }
            shiftedChar = Character.toString((char)((( ((charAscii-shiftFactor) + shiftInt) -1) % 26)+1 + shiftFactor));
            //Ciphertext = Ciphertext + String.valueOf(charAscii) + " " + String.valueOf((charAscii + shiftInt - shiftFactor)%26 + shiftFactor);
            Ciphertext = Ciphertext + String.valueOf(shiftedChar);
        }

        //Ciphertext = inputMessage;
        TextView messageOutputView = findViewById(R.id.resultText);
        messageOutputView.setText(Ciphertext);
    }
}
