// Copyright (C) 2011 Shane Conder & Lauren Darcey
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

#pragma version(1)

// Tell which java package name the reflected files should belong to
#pragma rs java_package_name(com.mamlambo.livewallpaper.stars)
#pragma stateFragment(parent)
// Built-in header with graphics API's
#include "rs_graphics.rsh"

// location of hidden text
int gTouchX;
int gTouchY;

rs_mesh starMesh;

typedef struct __attribute__((packed, aligned(4))) Star {
    float2 velocity;
    float2 position;
    uchar4 color;
} Star_t;
Star_t *star;




int root() {

    // Clear to the background color
    rsgClearColor(0.0f, 0.0f, 0.0f, 0.0f);


    // time since last update
    float dt = min(rsGetDt(), 0.1f);
    
    // dimens
    float w = rsgGetWidth();
    float h = rsgGetHeight();
    
    int starCount = rsAllocationGetDimX(rsGetAllocation(star));
    
    Star_t *pStar = star;
    
    for (int i=0; i < starCount; i++) {
        
        pStar->position.x += (pStar->velocity.x * dt);

        if (pStar->position.x > w) {
            pStar->position.x = 0+pStar->position.x - w;
        }

        pStar++;
    }
    rsgDrawMesh(starMesh);



    if (gTouchX != 0.0f) {
        rsgFontColor(1.0f, 1.0f, 1.0f, 1.0f);
        rsgDrawText("Hello Readers!", gTouchX, gTouchY);
    }


    return 30;
}




// This is invoked automatically when the script is created
void init() {
    gTouchX = 0.0f;
    gTouchY = 0.0f;
}

void initStars() {
    // dimens
    const float w = rsgGetWidth();
    const float h = rsgGetHeight();
    
    int starCount = rsAllocationGetDimX(rsGetAllocation(star));
    
    Star_t *pStar = star;
    for (int i=0; i < starCount; i++) {
        pStar->position.x = rsRand(w);
        pStar->position.y = rsRand(h);
        
        pStar->velocity.x = rsRand(100);
        pStar->velocity.y = 0;
        
        // brightness -- faster == brighter
        float b = pStar->velocity.x/100;
        uchar4 c = rsPackColorTo8888(b, b, b);
        pStar->color = c;
        pStar++;
    }
}

