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
package aztech.modern_industrialization.machines.guicomponents;

import aztech.modern_industrialization.machines.GuiComponents;
import aztech.modern_industrialization.machines.gui.ClientComponentRenderer;
import aztech.modern_industrialization.machines.gui.GuiComponent;
import aztech.modern_industrialization.machines.gui.MachineScreen;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class ReiSlotLocking {
    @FunctionalInterface
    public interface SlotLockable {
        void lockSlots(ResourceLocation recipeId, Inventory inventory);
    }

    public static class Server implements GuiComponent.Server<Boolean> {
        public final SlotLockable slotLockable;
        public final Supplier<Boolean> allowLocking;

        public Server(SlotLockable slotLockable, Supplier<Boolean> allowLocking) {
            this.slotLockable = slotLockable;
            this.allowLocking = allowLocking;
        }

        @Override
        public Boolean copyData() {
            return allowLocking.get();
        }

        @Override
        public boolean needsSync(Boolean cachedData) {
            return allowLocking.get() != cachedData;
        }

        @Override
        public void writeInitialData(FriendlyByteBuf buf) {
            writeCurrentData(buf);
        }

        @Override
        public void writeCurrentData(FriendlyByteBuf buf) {
            buf.writeBoolean(allowLocking.get());
        }

        @Override
        public ResourceLocation getId() {
            return GuiComponents.REI_SLOT_LOCKING;
        }
    }

    public static class Client implements GuiComponent.Client {
        private boolean allowLocking;

        public Client(FriendlyByteBuf initialData) {
            readCurrentData(initialData);
        }

        public boolean isLockingAllowed() {
            return allowLocking;
        }

        @Override
        public void readCurrentData(FriendlyByteBuf buf) {
            allowLocking = buf.readBoolean();
        }

        @Override
        public ClientComponentRenderer createRenderer(MachineScreen machineScreen) {
            // nothing to do;
            return (helper, matrices, x, y) -> {
            };
        }
    }
}
