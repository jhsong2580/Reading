package domain.ch04.item20;

public class SingerSongWriterByInterface implements SingerInterface, SongWriterInterface {

    @Override
    public void sing(String songName) {

    }

    @Override
    public String compose(int chartPosition) {
        return null;
    }
}
