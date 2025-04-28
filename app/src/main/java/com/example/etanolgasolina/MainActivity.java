// Pacote onde o app está organizado
package com.example.etanolgasolina;

// Importação das bibliotecas necessárias para funcionamento
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast; // Para mostrar mensagens curtas para o usuário

// Bibliotecas do AndroidX para lidar com bordas e layout
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

// Classe principal que representa a tela (Activity)
public class MainActivity extends AppCompatActivity {

    // Declaração das variáveis que vão representar os elementos da tela
    TextView txtResultado;
    EditText edtGasolina;
    EditText edtEtanol;
    Button btnCalcular;
    Button btnLimpar;

    // Método que é chamado quando a Activity é criada (iniciada)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // Inicializa a superclasse
        EdgeToEdge.enable(this); // Deixa o app "full screen", aproveitando a tela toda
        setContentView(R.layout.activity_main); // Define o layout da Activity

        // Ajuste para o app respeitar a área segura da tela (tipo a barra de status)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Faz a ligação entre as variáveis Java e os elementos visuais da tela
        txtResultado = findViewById(R.id.txtResultado);
        edtGasolina = findViewById(R.id.edtGasolina);
        edtEtanol = findViewById(R.id.edtEtanol);
        btnCalcular = findViewById(R.id.btnCalcular);
        btnLimpar = findViewById(R.id.btnLimpar);

        // Define o que acontece quando clicar no botão "Calcular"
        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcularMelhorOpcao(); // Chama o método que faz o cálculo
            }
        });

        // Define o que acontece quando clicar no botão "Limpar"
        btnLimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limparCampos(); // Chama o método que limpa os campos
            }
        });
    }

    // Método que calcula qual combustível é mais vantajoso
    private void calcularMelhorOpcao() {
        // Pega o texto digitado nos campos e remove espaços extras
        String gasolinaStr = edtGasolina.getText().toString().trim();
        String etanolStr = edtEtanol.getText().toString().trim();

        // Verifica se algum campo ficou vazio
        if (gasolinaStr.isEmpty() || etanolStr.isEmpty()) {
            Toast.makeText(this, "Por favor, preencha os dois campos.", Toast.LENGTH_SHORT).show();
            return; // Sai do método, não faz o cálculo
        }

        try {
            // Converte o texto dos campos para números (double)
            double precoGasolina = Double.parseDouble(gasolinaStr);
            double precoEtanol = Double.parseDouble(etanolStr);

            // Verifica se os preços são válidos (maiores que zero)
            if (precoGasolina <= 0 || precoEtanol <= 0) {
                Toast.makeText(this, "Os preços devem ser maiores que zero.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Calcula a proporção entre etanol e gasolina
            double resultado = precoEtanol / precoGasolina;

            // Decide qual combustível é melhor com base no valor 0.7
            if (resultado <= 0.7) {
                txtResultado.setText("Melhor abastecer com Etanol 🚗💨");
            } else {
                txtResultado.setText("Melhor abastecer com Gasolina 🚙⛽");
            }

        } catch (NumberFormatException e) {
            // Se o usuário digitar algo errado (tipo letra), mostra erro
            Toast.makeText(this, "Digite valores válidos (apenas números).", Toast.LENGTH_SHORT).show();
        }
    }

    // Método que limpa os campos de texto e reseta o resultado
    private void limparCampos() {
        edtGasolina.setText(""); // Limpa campo da gasolina
        edtEtanol.setText(""); // Limpa campo do etanol
        txtResultado.setText("Melhor abastecer com:"); // Reseta texto do resultado
        Toast.makeText(this, "Campos limpos.", Toast.LENGTH_SHORT).show(); // Mostra mensagem de confirmação
    }
}
