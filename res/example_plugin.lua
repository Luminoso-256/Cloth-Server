-- Example plugin for Stitch plugin API
function OnServerInit()
    -- Call something from the main server class
    minecraft_server:log("This is an example plugin hooking into the minecraft server class")
    return "HOOK_COMPLETE" -- Hooks will eventually have return states. For now this is a placeholder.
end