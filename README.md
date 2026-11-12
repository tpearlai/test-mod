# The Locust Horror — Forge 1.20.1 (alpha source)

## What is included
- Forge 1.20.1 / Java 17 source, 12 original supplied OGG sounds, original The Locust GLB.
- Prototype entity with stalk, haunt, chase, retreat states; scare audio and relocation, door knocking, boat dismount prevention.
- `mods.toml` declares **GFBS glTF 1.5.1+** as a mandatory dependency.
- GitHub Actions workflow fetches official Forge MDK Gradle wrapper and builds a JAR artifact.

## IMPORTANT: not yet complete
- **GLB rendering and animations are NOT yet connected** to GFBS's Java API. The entity uses a temporary vanilla zombie renderer. GLB is packaged in the JAR but is not rendered.
- No natural spawning, spawn egg or summon command beyond vanilla `/summon locusthorror:the_locust` (with cheats enabled).
- Chase music is currently a positional one-shot; no client-side looping/fade controller.
- Window stalking, glass scratching, pillar climbing, sophisticated pathfinding, and jumpscare camera are planned but not implemented.
- This package has **not been compiled or run-tested** in this environment. Build may expose API/signature issues; use GitHub Actions log to diagnose.

## Build via GitHub
1. Extract this ZIP locally. Upload its **contents** to a new GitHub repository (including `.github/workflows/build.yml`).
2. GitHub Actions > Build Forge 1.20.1 mod > Run workflow (or push to `main`).
3. Download `TheLocustHorror-JAR` from the completed workflow's Artifacts section.
4. Install Forge 1.20.1, Java 17, and the compatible GFBS glTF library JAR in `mods/` alongside this mod JAR.
5. Test in a throwaway world first. Run `/summon locusthorror:the_locust`.

## Dependencies
GFBS: https://github.com/LytharaLab/GFBS-glTF

## Next phase
Integrate GFBS client renderer and animation names after checking exact versioned API; implement proximity window/door stalking, anti-pillar logic, synced chase audio, and configurable jumpscares.
