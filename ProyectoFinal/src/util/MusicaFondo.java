package util;

import javax.sound.sampled.*;
import java.io.File;

public class MusicaFondo {

    private Clip clip;

    public void reproducirMusica(String rutaArchivo) {
        try {
            File archivo = new File(rutaArchivo);

            if (!archivo.exists()) {
                System.out.println("Archivo de música no encontrado: " + rutaArchivo);
                return;
            }

            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(archivo);

            clip = AudioSystem.getClip();
            clip.open(audioInputStream);

            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al intentar reproducir la música.");
        }
    }

    public void detenerMusica() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();
        }
    }
}
