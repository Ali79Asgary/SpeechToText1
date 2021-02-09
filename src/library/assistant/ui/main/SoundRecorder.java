package library.assistant.ui.main;

import javafx.concurrent.Task;

import javax.sound.sampled.*;
import java.io.File;
import java.util.Objects;

public class SoundRecorder extends Task<Void> {

    static final long RECORD_TIME = 60000;

    int filesCount = Objects.requireNonNull(new File("D:\\IntelliJ\\Mr.Rasekh_JavaFX\\Library-Assistant\\AudioFiles").listFiles()).length;

    File wavFile = new File("D:\\IntelliJ\\Mr.Rasekh_JavaFX\\Library-Assistant\\AudioFiles\\RecordFile"+filesCount+".wav");

    AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;

    TargetDataLine targetDataLine;

    @Override
    protected Void call() throws Exception {
        try {
            AudioFormat format = getAudioFormat();
//            final int bufferByteSize = 2048;
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

            if (!AudioSystem.isLineSupported(info)){
                System.out.println("Line not supported!");
                System.exit(0);
            }
            targetDataLine = (TargetDataLine) AudioSystem.getLine(info);
            targetDataLine.open(format);

//            byte[] buf = new byte[bufferByteSize];
//            float[] samples = new float[bufferByteSize / 2];
//
//            float lastPeak = 0f;

            targetDataLine.start();

//            for(int b; (b = targetDataLine.read(buf, 0, buf.length)) > -1;) {
//
//                // convert bytes to samples here
//                for(int i = 0, s = 0; i < b;) {
//                    int sample = 0;
//
//                    sample |= buf[i++] & 0xFF; // (reverse these two lines
//                    sample |= buf[i++] << 8;   //  if the format is big endian)
//
//                    // normalize to range of +/-1.0f
//                    samples[s++] = sample / 32768f;
//                }
//
//                float rms = 0f;
//                float peak = 0f;
//                for(float sample : samples) {
//
//                    float abs = Math.abs(sample);
//                    if(abs > peak) {
//                        peak = abs;
//                    }
//
//                    rms += sample * sample;
//                }
//
//                rms = (float)Math.sqrt(rms / samples.length);
//
//                if(lastPeak > peak) {
//                    peak = lastPeak * 0.875f;
//                }
//
//                lastPeak = peak;
//
//                setMeterOnEDT(rms, peak);

            System.out.println("Start Capturing!");

            AudioInputStream audioInputStream = new AudioInputStream(targetDataLine);

            System.out.println("Start Recording!");

            AudioSystem.write(audioInputStream, fileType, wavFile);

        } catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return null;
    }

    private AudioFormat getAudioFormat() {
//        float sampleRate = 16000;
        float sampleRate = 44100f;
//        int sampleSizeInBits = 8;
        int sampleSizeInBits = 16;
//        int channels = 2;
        int channels = 1;
//        boolean signed = true;
        boolean signed = true;
//        boolean bigEndian = true;
        boolean bigEndian = false;
        AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
        return format;
    }

    public void finish(){
        targetDataLine.stop();
        targetDataLine.close();
        System.out.println("Finished!");
    }
}
