package dev.maxkach.shaders.glitch

import org.intellij.lang.annotations.Language

@Language("AGSL")
val GLITCH_SHADER = """
uniform shader image;
uniform float2 imageSize;
uniform float time;
uniform float intensity;
uniform float realRandom;
uniform float slices;
uniform float noiseIntensity;
uniform float colorBarsEnabled;
uniform float rgbSplitIntensity;

float rand(float x) {
    return fract(sin(x * 1234.567 + 0.123) * 43758.5453);
}

float rand2(vec2 v) {
    return fract(sin(dot(v, vec2(12.9898, 78.233))) * 43758.5453);
}

float randDirection(int sliceIndex) {
    return (rand(float(sliceIndex) + 99.0) > 0.5) ? 1.0 : -1.0;
}

vec2 applySliceOffset(vec2 uv) {
    int numSlices = int(slices);
    float sliceHeight = 1.0 / float(numSlices);
    int sliceIndex = int(uv.y / sliceHeight);

    float frame = floor(time);
    float r = rand(float(sliceIndex) + frame + realRandom * 100.0);

    float glitch = 0.0;
    if (r > 0.65) {
        glitch = (r - 0.65) * 4.0 * intensity;
    }

    float direction = randDirection(sliceIndex);
    float offset = glitch * 0.08 * direction;
    uv.x += offset;

    return clamp(uv, 0.0, 1.0);
}

vec4 sampleRgbSplit(vec2 baseUv) {
    float splitAmount = 0.005 * rgbSplitIntensity;

    vec2 uvR = clamp(baseUv + vec2(-splitAmount, 0.0), 0.0, 1.0);
    vec2 uvG = baseUv;
    vec2 uvB = clamp(baseUv + vec2(splitAmount, 0.0), 0.0, 1.0);

    vec4 cR = image.eval(uvR * imageSize);
    vec4 cG = image.eval(uvG * imageSize);
    vec4 cB = image.eval(uvB * imageSize);

    float a = cG.a;
    return vec4(min(cR.r, a), cG.g, min(cB.b, a), a);
}

float verticalNoise(vec2 uv) {
    float line = fract(uv.y * imageSize.y * 0.5);
    float mask = step(0.5, line);
    float noise = rand2(vec2(uv.x * 40.0, floor(uv.y * imageSize.y)));
    return mix(1.0, 0.85, mask * noise * noiseIntensity);
}

vec4 getSolidColorBar(vec2 uv) {
    int numSlices = int(slices);
    float sliceHeight = 1.0 / float(numSlices);
    int sliceIndex = int(uv.y / sliceHeight);
    float sliceY = float(sliceIndex) * sliceHeight;

    float rnd = rand2(vec2(intensity * realRandom, sliceY));
    if ((rnd * 2.5 + 0.5) < intensity) {
        if (realRandom > 0.67) {
            return vec4(0.0, 1.0, 0.3, 1.0);
        } else if (realRandom > 0.33) {
            return vec4(1.0, 0.0, 0.85, 1.0);
        } else {
            return vec4(0.0, 0.85, 1.0, 1.0);
        }
    }

    return vec4(0.0, 0.0, 0.0, 0.0);
}

vec4 main(vec2 fragCoord) {
    // image.eval is premultiplied. Keep the source alpha so transparent pixels stay transparent.
    vec2 uv = fragCoord / imageSize;

    vec4 color = vec4(0.0);
    bool isColorBar = false;
    if (colorBarsEnabled > 0.5) {
        vec4 solidColor = getSolidColorBar(uv);
        if (solidColor.a > 0.0) {
            color = solidColor;
            isColorBar = true;
        }
    }
    if (!isColorBar) {
        uv = applySliceOffset(uv);
        color = sampleRgbSplit(uv);
    }

    color.rgb *= verticalNoise(uv);

    float brightness = dot(color.rgb / max(color.a, 0.0001), vec3(0.299, 0.587, 0.114));
    float darkness = 1.0 - brightness;
    vec3 tintColor = vec3(0.2, 0.3, 0.35) * color.a;
    color.rgb = mix(color.rgb, tintColor, darkness * 0.15 * intensity);

    return color;
}
"""
