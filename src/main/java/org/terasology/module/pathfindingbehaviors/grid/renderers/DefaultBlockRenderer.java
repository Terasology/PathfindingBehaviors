// Copyright 2021 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.module.pathfindingbehaviors.grid.renderers;

import org.joml.Vector2fc;
import org.joml.Vector3i;
import org.terasology.engine.entitySystem.systems.BaseComponentSystem;
import org.terasology.engine.entitySystem.systems.RegisterSystem;
import org.terasology.engine.registry.In;
import org.terasology.engine.registry.Share;
import org.terasology.engine.rendering.assets.texture.Texture;
import org.terasology.engine.utilities.Assets;
import org.terasology.engine.world.WorldProvider;
import org.terasology.engine.world.block.Block;
import org.terasology.engine.world.block.BlockPart;
import org.terasology.engine.world.block.tiles.WorldAtlas;
import org.terasology.module.pathfindingbehaviors.grid.BlockRenderer;
import org.terasology.joml.geom.Rectanglei;
import org.terasology.nui.Canvas;
import org.terasology.nui.Color;
import org.terasology.nui.ScaleMode;

@RegisterSystem
@Share(value = DefaultBlockRenderer.class)
public class DefaultBlockRenderer extends BaseComponentSystem implements BlockRenderer {
    @In
    private WorldProvider worldProvider;
    @In
    private WorldAtlas worldAtlas;

    private Texture terrainTex;
    private float relativeTileSize;

    @Override
    public void initialise() {
        terrainTex = Assets.getTexture("engine:terrain").get();
        relativeTileSize = 0.0625f;
    }

    @Override
    public void renderBlock(Canvas canvas, Vector3i blockPos, Rectanglei screenRegion) {
        Color color = new Color(1f, 1f, 1f, 1f);
        int depth = 0;
        blockPos.y++;
        float max = 10;
        while (blockPos.y >= 0) {
            Block block = worldProvider.getBlock(blockPos);

            if (!block.isTranslucent()) {
                Vector2fc textureAtlasPos = block.getPrimaryAppearance().getTextureAtlasPos(BlockPart.TOP);
                canvas.drawTextureRaw(terrainTex, screenRegion, color, ScaleMode.SCALE_FILL,
                        textureAtlasPos.x(), textureAtlasPos.y(), relativeTileSize, relativeTileSize);
                break;
            } // TODO alpha blocks: else { }
            
            blockPos.y--;
            depth++;
            if (depth >= max) {
                break;
            }
            color = new Color(1 - depth / max, 1 - depth / max, 1 - depth / max, 1);
        }

    }
}

