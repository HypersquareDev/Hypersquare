# Hypersquare

Hypersquare is a remake of the popular server Diamondfire.
We aim to include all the most requested features by the community that Diamondfire denied.


## Contributing

You can contribute by fixing inefficiencies, mistakes or oversights made by us in the code.

### Dev Setup

1. Get the latest build of hypersquare on github
2. Run the executable in an empty folder if you are using a CLI then run the command `java -jar hypersquare.jar devsetup`
3. On a graphical interface click the button and wait
4. When you first run the server it will have errors so stop the server
5. In plugins/SlimeWorldManager/sources.yml look under `mongodb:` and set `enabled: true` and fill out database info sent by Chicken on discord or use your own database
6. In plugins/hypersquare/config.yml `DB_PASS` should be set to the database URL and `DB_NAME` to your database name if not given by Chicken

## Discord 

You can contact us or join the community by joining the discord [here](https://discord.gg/uyXGY73kdw)
