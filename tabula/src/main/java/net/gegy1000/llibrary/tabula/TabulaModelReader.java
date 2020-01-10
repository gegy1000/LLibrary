package net.gegy1000.llibrary.tabula;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.JsonOps;
import net.minecraft.client.renderer.texture.NativeImage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public final class TabulaModelReader implements AutoCloseable {
    private final FileSystem fs;

    private TabulaModelReader(FileSystem fs) {
        this.fs = fs;
    }

    public static TabulaModelReader open(Path path) throws IOException {
        FileSystem fs = FileSystems.newFileSystem(path, null);
        return new TabulaModelReader(fs);
    }

    public TabulaModel readModel() throws IOException {
        Path path = this.fs.getPath("model.json");
        try (InputStream input = Files.newInputStream(path)) {
            JsonElement root = new JsonParser().parse(new BufferedReader(new InputStreamReader(input)));
            return TabulaModel.readFrom(new Dynamic<>(JsonOps.INSTANCE, root));
        }
    }

    public Optional<TabulaTexture> readTexture() throws IOException {
        Path path = this.fs.getPath("texture.png");
        try (InputStream input = Files.newInputStream(path)) {
            NativeImage image = NativeImage.read(NativeImage.PixelFormat.RGBA, input);
            return Optional.of(new TabulaTexture(image));
        }
    }

    @Override
    public void close() throws IOException {
        this.fs.close();
    }
}
