module.exports = function(grunt) {

  require('load-grunt-tasks')(grunt);

  var pkg = grunt.file.readJSON('package.json');

  grunt.initConfig({

    pkg: pkg,

    clean: ['bin/'],

    copy: {
      main: {
        expand: true,
        src: 'static/**/*',
        dest: 'bin/',
        filter: 'isFile'
      }
    },

    delta: {
      options: {
        dateFormat: function(time) {
          grunt.log.writeln('The watch finished in ' + time + ' ms at ' + (new Date()).toString());
          grunt.log.writeln('Waiting for more changes...');
        },
        expand: true,
        cwd: 'src/',
        livereload: true
      },
      jade: {
        options: {
          livereload: true
        },
        files: {
          'bin/index.html': 'src/index.jade'
        },
        files: ['**/*.jade'],
        tasks: ['jade']
      },
      sass: {
        options: {
          livereload: true
        },
        files: ['**/*.sass'],
        tasks: ['compass']
      },
      livescript: {
        options: {
          livereload: true
        },
        files: ['**/*.ls'],
        tasks: ['livescript']
      }
    },

    jade: {
      options: {
        pretty: true,
        i18n: {
          locales: 'ch.json',
          namespace: '$ch',
          localeExtension: 'true'
        }
      },
      index: {
        files: {
          'bin/index.html': 'src/index.jade'
        }
      }
    },

    compass: {
      compile: {
        options: {
          sassDir: 'src/sass',
          cssDir: 'bin/css',
          environment: 'production'
        }
      }
    },

    livescript: {
      options: {
        bare: false
      },
      compile: {
        expand: true,
        cwd: 'src/livescript',
        src: ['*.ls'],
        dest: 'bin/js/',
        ext: '.js'
      }
    },

    jshint: {
      files: ['Gruntfile.js', 'src/**/*.js', 'test/**/*.js'],
      options: {
        globals: {
          jQuery: true
        }
      }
    }

  });

  grunt.renameTask('watch', 'delta');

  grunt.registerTask('default', ['clean', 'copy', 'jade', 'compass', 'livescript']);
  grunt.registerTask('build', ['clean', 'copy', 'jade', 'compass', 'livescript']);

  grunt.registerTask('watch', ['build', 'delta']);

};
