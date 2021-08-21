package com.devin12422.tweaks;

import io.github.kosmx.bendylib.ModelPartAccessor;
import io.github.kosmx.bendylib.MutableCuboid;
import io.github.kosmx.bendylib.impl.BendableCuboid;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3d;
import net.minecraft.util.math.Vec3f;

//import 
import java.util.function.Consumer;

import com.devin12422.tweaks.util.Pair;

//Until I don't have to modify bendy-lib, this will work properly
public class BendableModelPart {

    ModelPart modelPart;

    public BendableModelPart(ModelPart modelPart){
        this.modelPart = modelPart;
    }

    public void bend(float a, float b){
        ModelPartAccessor.optionalGetCuboid(modelPart, 0).ifPresent(mutableCuboid -> ((BendableCuboid)mutableCuboid.getAndActivateMutator("bend")).applyBend(a, b));
    }

    public void bend(Pair<Float, Float> pair){
        if(pair != null) {
            this.bend(pair.getLeft(), pair.getRight());
        }
        else {
            //ModelPartAccessor.getCuboid(modelPart, 0).getAndActivateMutator(null);
            ModelPartAccessor.optionalGetCuboid(modelPart, 0).ifPresent(mutableCuboid -> mutableCuboid.getAndActivateMutator(null));
        }
    }


    public static void roteteMatrixStack(MatrixStack matrices, Pair<Float, Float> pair){
        float offset = 0.375f;
        matrices.translate(0, offset, 0);
        float bend = pair.getRight();
        float axisf = - pair.getLeft();
        Vec3f axis = new Vec3f((float) Math.cos(axisf), 0, (float) Math.sin(axisf));
        //return this.setRotation(axis.getRadialQuaternion(bend));
        matrices.multiply(axis.getDegreesQuaternion(bend));
        matrices.translate(0, - offset, 0);
    }
}