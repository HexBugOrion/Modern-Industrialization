/*
 * MIT License
 *
 * Copyright (c) 2020 Azercoco & Technici4n
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package aztech.modern_industrialization.pipes.api;

import java.util.Collection;
import java.util.function.Function;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.BlockAndTintGetter;
import org.jetbrains.annotations.Nullable;

// TODO: Refactor to split api and impl correctly, and provide building blocks in API if necessary
public interface PipeRenderer {
    /**
     * Draw the connections for a logical slot.
     * 
     * @param ctx         Render context.
     * @param logicalSlot The logical slot, so 0 for center, 1 for lower and 2 for
     *                    upper.
     * @param connections For every logical slot, then for every direction, the
     *                    connection type or null for no connection.
     */
    void draw(@Nullable BlockAndTintGetter view, @Nullable BlockPos pos, RenderContext ctx, int logicalSlot, PipeEndpointType[][] connections,
            CompoundTag customData);

    interface Factory {
        Collection<Material> getSpriteDependencies();

        PipeRenderer create(Function<Material, TextureAtlasSprite> textureGetter);
    }
}
